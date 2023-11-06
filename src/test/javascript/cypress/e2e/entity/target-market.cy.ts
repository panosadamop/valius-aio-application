import {
  entityTableSelector,
  entityDetailsButtonSelector,
  entityDetailsBackButtonSelector,
  entityCreateButtonSelector,
  entityCreateSaveButtonSelector,
  entityCreateCancelButtonSelector,
  entityEditButtonSelector,
  entityDeleteButtonSelector,
  entityConfirmDeleteButtonSelector,
} from '../../support/entity';

describe('TargetMarket e2e test', () => {
  const targetMarketPageUrl = '/target-market';
  const targetMarketPageUrlPattern = new RegExp('/target-market(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const targetMarketSample = { value: 'Fish', language: 'GREEK' };

  let targetMarket;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/target-markets+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/target-markets').as('postEntityRequest');
    cy.intercept('DELETE', '/api/target-markets/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (targetMarket) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/target-markets/${targetMarket.id}`,
      }).then(() => {
        targetMarket = undefined;
      });
    }
  });

  it('TargetMarkets menu should load TargetMarkets page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('target-market');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('TargetMarket').should('exist');
    cy.url().should('match', targetMarketPageUrlPattern);
  });

  describe('TargetMarket page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(targetMarketPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create TargetMarket page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/target-market/new$'));
        cy.getEntityCreateUpdateHeading('TargetMarket');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', targetMarketPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/target-markets',
          body: targetMarketSample,
        }).then(({ body }) => {
          targetMarket = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/target-markets+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/target-markets?page=0&size=20>; rel="last",<http://localhost/api/target-markets?page=0&size=20>; rel="first"',
              },
              body: [targetMarket],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(targetMarketPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details TargetMarket page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('targetMarket');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', targetMarketPageUrlPattern);
      });

      it('edit button click should load edit TargetMarket page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('TargetMarket');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', targetMarketPageUrlPattern);
      });

      it('edit button click should load edit TargetMarket page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('TargetMarket');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', targetMarketPageUrlPattern);
      });

      it('last delete button click should delete instance of TargetMarket', () => {
        cy.intercept('GET', '/api/target-markets/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('targetMarket').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', targetMarketPageUrlPattern);

        targetMarket = undefined;
      });
    });
  });

  describe('new TargetMarket page', () => {
    beforeEach(() => {
      cy.visit(`${targetMarketPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('TargetMarket');
    });

    it('should create an instance of TargetMarket', () => {
      cy.get(`[data-cy="value"]`).type('Maine').should('have.value', 'Maine');

      cy.get(`[data-cy="description"]`)
        .type('../fake-data/blob/hipster.txt')
        .invoke('val')
        .should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="language"]`).select('GREEK');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        targetMarket = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', targetMarketPageUrlPattern);
    });
  });
});
