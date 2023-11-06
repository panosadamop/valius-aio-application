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

describe('MarketSegmentationTypeB2cAlt e2e test', () => {
  const marketSegmentationTypeB2cAltPageUrl = '/market-segmentation-type-b-2-c-alt';
  const marketSegmentationTypeB2cAltPageUrlPattern = new RegExp('/market-segmentation-type-b-2-c-alt(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const marketSegmentationTypeB2cAltSample = { value: 'Bedfordshire Fresh', language: 'GREEK' };

  let marketSegmentationTypeB2cAlt;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/market-segmentation-type-b-2-c-alts+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/market-segmentation-type-b-2-c-alts').as('postEntityRequest');
    cy.intercept('DELETE', '/api/market-segmentation-type-b-2-c-alts/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (marketSegmentationTypeB2cAlt) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/market-segmentation-type-b-2-c-alts/${marketSegmentationTypeB2cAlt.id}`,
      }).then(() => {
        marketSegmentationTypeB2cAlt = undefined;
      });
    }
  });

  it('MarketSegmentationTypeB2cAlts menu should load MarketSegmentationTypeB2cAlts page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('market-segmentation-type-b-2-c-alt');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('MarketSegmentationTypeB2cAlt').should('exist');
    cy.url().should('match', marketSegmentationTypeB2cAltPageUrlPattern);
  });

  describe('MarketSegmentationTypeB2cAlt page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(marketSegmentationTypeB2cAltPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create MarketSegmentationTypeB2cAlt page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/market-segmentation-type-b-2-c-alt/new$'));
        cy.getEntityCreateUpdateHeading('MarketSegmentationTypeB2cAlt');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', marketSegmentationTypeB2cAltPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/market-segmentation-type-b-2-c-alts',
          body: marketSegmentationTypeB2cAltSample,
        }).then(({ body }) => {
          marketSegmentationTypeB2cAlt = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/market-segmentation-type-b-2-c-alts+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/market-segmentation-type-b-2-c-alts?page=0&size=20>; rel="last",<http://localhost/api/market-segmentation-type-b-2-c-alts?page=0&size=20>; rel="first"',
              },
              body: [marketSegmentationTypeB2cAlt],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(marketSegmentationTypeB2cAltPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details MarketSegmentationTypeB2cAlt page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('marketSegmentationTypeB2cAlt');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', marketSegmentationTypeB2cAltPageUrlPattern);
      });

      it('edit button click should load edit MarketSegmentationTypeB2cAlt page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('MarketSegmentationTypeB2cAlt');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', marketSegmentationTypeB2cAltPageUrlPattern);
      });

      it('edit button click should load edit MarketSegmentationTypeB2cAlt page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('MarketSegmentationTypeB2cAlt');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', marketSegmentationTypeB2cAltPageUrlPattern);
      });

      it('last delete button click should delete instance of MarketSegmentationTypeB2cAlt', () => {
        cy.intercept('GET', '/api/market-segmentation-type-b-2-c-alts/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('marketSegmentationTypeB2cAlt').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', marketSegmentationTypeB2cAltPageUrlPattern);

        marketSegmentationTypeB2cAlt = undefined;
      });
    });
  });

  describe('new MarketSegmentationTypeB2cAlt page', () => {
    beforeEach(() => {
      cy.visit(`${marketSegmentationTypeB2cAltPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('MarketSegmentationTypeB2cAlt');
    });

    it('should create an instance of MarketSegmentationTypeB2cAlt', () => {
      cy.get(`[data-cy="value"]`).type('Home improvement').should('have.value', 'Home improvement');

      cy.get(`[data-cy="description"]`)
        .type('../fake-data/blob/hipster.txt')
        .invoke('val')
        .should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="language"]`).select('ENGLISH');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        marketSegmentationTypeB2cAlt = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', marketSegmentationTypeB2cAltPageUrlPattern);
    });
  });
});
