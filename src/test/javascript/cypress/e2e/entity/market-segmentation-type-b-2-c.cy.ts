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

describe('MarketSegmentationTypeB2c e2e test', () => {
  const marketSegmentationTypeB2cPageUrl = '/market-segmentation-type-b-2-c';
  const marketSegmentationTypeB2cPageUrlPattern = new RegExp('/market-segmentation-type-b-2-c(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const marketSegmentationTypeB2cSample = { value: 'Berkshire Universal', language: 'GREEK' };

  let marketSegmentationTypeB2c;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/market-segmentation-type-b-2-cs+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/market-segmentation-type-b-2-cs').as('postEntityRequest');
    cy.intercept('DELETE', '/api/market-segmentation-type-b-2-cs/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (marketSegmentationTypeB2c) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/market-segmentation-type-b-2-cs/${marketSegmentationTypeB2c.id}`,
      }).then(() => {
        marketSegmentationTypeB2c = undefined;
      });
    }
  });

  it('MarketSegmentationTypeB2cs menu should load MarketSegmentationTypeB2cs page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('market-segmentation-type-b-2-c');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('MarketSegmentationTypeB2c').should('exist');
    cy.url().should('match', marketSegmentationTypeB2cPageUrlPattern);
  });

  describe('MarketSegmentationTypeB2c page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(marketSegmentationTypeB2cPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create MarketSegmentationTypeB2c page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/market-segmentation-type-b-2-c/new$'));
        cy.getEntityCreateUpdateHeading('MarketSegmentationTypeB2c');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', marketSegmentationTypeB2cPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/market-segmentation-type-b-2-cs',
          body: marketSegmentationTypeB2cSample,
        }).then(({ body }) => {
          marketSegmentationTypeB2c = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/market-segmentation-type-b-2-cs+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/market-segmentation-type-b-2-cs?page=0&size=20>; rel="last",<http://localhost/api/market-segmentation-type-b-2-cs?page=0&size=20>; rel="first"',
              },
              body: [marketSegmentationTypeB2c],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(marketSegmentationTypeB2cPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details MarketSegmentationTypeB2c page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('marketSegmentationTypeB2c');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', marketSegmentationTypeB2cPageUrlPattern);
      });

      it('edit button click should load edit MarketSegmentationTypeB2c page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('MarketSegmentationTypeB2c');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', marketSegmentationTypeB2cPageUrlPattern);
      });

      it('edit button click should load edit MarketSegmentationTypeB2c page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('MarketSegmentationTypeB2c');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', marketSegmentationTypeB2cPageUrlPattern);
      });

      it('last delete button click should delete instance of MarketSegmentationTypeB2c', () => {
        cy.intercept('GET', '/api/market-segmentation-type-b-2-cs/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('marketSegmentationTypeB2c').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', marketSegmentationTypeB2cPageUrlPattern);

        marketSegmentationTypeB2c = undefined;
      });
    });
  });

  describe('new MarketSegmentationTypeB2c page', () => {
    beforeEach(() => {
      cy.visit(`${marketSegmentationTypeB2cPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('MarketSegmentationTypeB2c');
    });

    it('should create an instance of MarketSegmentationTypeB2c', () => {
      cy.get(`[data-cy="value"]`).type('Plastic').should('have.value', 'Plastic');

      cy.get(`[data-cy="description"]`)
        .type('../fake-data/blob/hipster.txt')
        .invoke('val')
        .should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="language"]`).select('GREEK');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        marketSegmentationTypeB2c = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', marketSegmentationTypeB2cPageUrlPattern);
    });
  });
});
